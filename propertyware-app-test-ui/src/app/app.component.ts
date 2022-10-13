import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'propertyware-app-test-ui';
  websiteList: any = ['LOCAL', 'SIT', 'SAT'];
  selectedEnv:string='LOCAL';

  form = new FormGroup({
    website: new FormControl('', Validators.required)
  });

  get f(){
    return this.form.controls;
  }

  changeWebsite(e:any) {
    console.log(e.target.value);
    this.selectedEnv = e.target.value;
  }
}
