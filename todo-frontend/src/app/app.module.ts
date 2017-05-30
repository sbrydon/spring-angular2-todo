import { NgModule, InjectionToken } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppConfigModule } from './app-config.module';

import { AuthGuard} from './auth.guard';
import { AuthService } from './auth.service';
import { TodoService } from './todo.service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login.component';
import { RegistrationComponent } from './registration.component';
import { PagerComponent } from './pager.component';
import { TodoHeaderComponent } from './todo-header.component';
import { TodoListComponent } from './todo-list.component';
import { TodoListItemComponent } from './todo-list-item.component';

@NgModule({
	imports: [ 
		BrowserModule, 
		AppRoutingModule,
		FormsModule,
		HttpModule,
		NgbModule.forRoot(),
		AppConfigModule
	],
	declarations: [ 
		AppComponent, 
		LoginComponent,
		RegistrationComponent,
		PagerComponent,
		TodoHeaderComponent,
		TodoListComponent, 
		TodoListItemComponent 
	],
	providers: [
		AuthGuard, 
		AuthService,
		TodoService
	],
	bootstrap: [ AppComponent ]
})
export class AppModule { }
