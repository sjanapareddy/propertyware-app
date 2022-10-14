import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators} from '@angular/forms';
interface Report {
  id: number;
  endpoint: string;
  action: string;
  group: string;
  input: string;
  output: string;
  executionTime: number;
  lastTested: number;
  result: string;
  url: string;
  isSelected:boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {

  masterSelected:boolean;
  checkedList:any;
  selectedData:any;
  loader: boolean = false;
  isValid: boolean = false;

  list_of_objects: any = [];
  constructor(private http: HttpClient){
    this.masterSelected = false;
    this.getCheckedItemList();
  }

  displayStyle = "none";
  envName: string = "";
  orgName: string = "";

  openPopup(e:any) {

    if(e.output !=null)
    this.selectedData = e.output;
    else
      this.selectedData = "";

    this.displayStyle = "block";
  }
  openInputPopup(e:any) {

    if(e.input !=null)
      this.selectedData = e.input;
    else
      this.selectedData = "";

    this.displayStyle = "block";
  }
  closePopup() {
    this.displayStyle = "none";
  }

  searchTerm: string = '';
  reportData: Report[] = [];
  allCountries: Report[] = [];

  title = 'propertyware-app-test-ui';
  orgList: any = [];
  selectedEnv:number=0;
  selectedOrg:number=0;
  body: any=[];
  errorMessage: string='';

  ngOnInit(): void {
    this.getEnvironments();
  }

  form = new FormGroup({
    website: new FormControl('', Validators.required)
  });

  get f(){
    return this.form.controls;
  }

  changeEnv(e:any) {
    this.selectedEnv = e.target.value;
    this.envName = this.envData.filter((x: any) => x.id == e.target.value)[0].name;
    this.getOrgsList(e.target.value);
  }

  changeOrg(e:any) {
    this.selectedOrg = e.target.value;
    this.orgName = this.orgList.filter((x: any) => x.id == e.target.value)[0].name;
    this.getAPITestData(this.selectedEnv, this.selectedOrg);
  }


  _keys:any  = [];
  envData:any  = [];

  getEnvironments(): void {
    this.loader = true;
    this.http
      .get("./pw/api/environments")
      .subscribe((data: any) => {
        this.envData = data;
        this.loader = false;
      });
  }

  getOrgsList(id: number): void {
    this.loader = true;
     this.http
      .get("./pw/api/environments/"+id+"/organizations")
      .subscribe((data: any) => {
        this.orgList = data;
        this.loader = false;
      });
  }

  getAPITestData(envId:number, orgId:number): void {
    this.loader = true;
    this.http
      .get<Report[]>("./pw/api/environments/"+envId+"/organizations/"+orgId+"/executions")
      .subscribe((data: Report[]) => {
        debugger
        this.reportData = data;
        this.allCountries = this.reportData;
        this.loader = false;
      });
  }

  search(event: any): void {
    const searchFilter = event.target?.value;
    this.reportData = this.allCountries.filter((val) =>
      val.endpoint.includes(searchFilter)
    );
  }

  checkUncheckAll() {
    for (var i = 0; i < this.reportData.length; i++) {
      this.reportData[i].isSelected = this.masterSelected;
    }
    this.getCheckedItemList();
    if(this.checkedList.length==0)
      this.isValid = false;
    else
      this.isValid = true;
  }

  isAllSelected() {
    this.masterSelected = this.reportData.every(function(item:any) {
      return item.isSelected == true;
    })
    this.getCheckedItemList();

    if(this.checkedList.length==0)
      this.isValid = false;
    else
      this.isValid = true;
  }

  getCheckedItemList(){
    this.checkedList = [];
    for (var i = 0; i < this.reportData.length; i++) {
      if(this.reportData[i].isSelected)
        this.checkedList.push(this.reportData[i]);
    }
  }
  resetFormData() {
    for (var i = 0; i < this.reportData.length; i++) {
      this.reportData[i].isSelected = false;
    }
    this.masterSelected = false;
    this.loader = false;
    this.isValid = false;
    this.errorMessage='';
    this.getCheckedItemList();
  }

  reTestRestApi(): void {
    this.loader = true;
    this.isValid = true;
    this.errorMessage = '';

    for (let list of this.checkedList) {
      this.list_of_objects.push(list.id);
    }

    let url = "./pw/api/environments/"+this.selectedEnv+"/organizations/"+this.selectedOrg+"/executions";
    this.http.put<any>(url, this.list_of_objects)
      .subscribe({
        next: data => {
          this.errorMessage = data.message;
          this.loader = false;
          this.getAPITestData(this.selectedEnv, this.selectedOrg);
          console.log("this.errorMessage " + JSON.stringify(this.errorMessage));
        },
        error: error => {
          this.errorMessage = error.message;
          console.error('There was an error!', error);
          this.loader = false;
        }
      });
    this.list_of_objects = [];

  }
}
