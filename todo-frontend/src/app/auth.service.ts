import { Injectable, Inject } from '@angular/core';
import { Http, Headers } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { APP_CONFIG, AppConfig } from './app-config.module';
import { Credentials } from './credentials';
 
@Injectable()
export class AuthService {
    private apiUrl: string;

    constructor(@Inject(APP_CONFIG) private config: AppConfig, private http: Http) {
        this.apiUrl = config.apiUrl;
    }

    register(credentials: Credentials): Promise<any> {
        return this.http.post(this.apiUrl + '/user/registration', credentials)
            .toPromise()
            .catch(this.onRegisterFailure);
    }

    private onRegisterFailure(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
 
    login(credentials: Credentials): Promise<void> {
    	let headers = new Headers();
        let encoded = btoa(credentials.username + ':' + credentials.password);
		headers.append('Authorization', 'Basic ' + encoded); 

		return this.http.options(this.apiUrl + '/todos', { headers: headers })
			.toPromise()
			.then(() => this.onLoginSuccess(credentials))
			.catch(() => this.onLoginFailure);
    }

    private onLoginFailure(error: any): Promise<any> {
        console.error('An error occurred', error);
        this.logout();

        return Promise.reject(error.message || error);
    }

    private onLoginSuccess(credentials: Credentials): void {
        let json = JSON.stringify(credentials);
        localStorage.setItem('credentials', json);
    }

    logout(): void {
    	localStorage.removeItem('credentials');
    }
}
