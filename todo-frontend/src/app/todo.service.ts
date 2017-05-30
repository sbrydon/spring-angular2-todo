import { Injectable, Inject } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { APP_CONFIG, AppConfig } from './app-config.module';
import { GetAllTodosResult, Todo } from './todo';
import { Credentials } from './credentials';

@Injectable()
export class TodoService {
	private apiUrl: string;

    constructor(@Inject(APP_CONFIG) private config: AppConfig, private http: Http) {
        this.apiUrl = config.apiUrl;
    }

	getAll(page: number = 0, done: boolean = null): Promise<GetAllTodosResult> {
		let url = this.apiUrl + '/todos?page=' + page + '&size=5&sort=created,desc';
		if (done !== null) {
			url += '&done=' + done;
		}

		return this.http.get(url, this.getHeaders())
			.toPromise()
			.then(response => response.json() as GetAllTodosResult)
			.catch(this.onError);
	}

	post(body: string): Promise<Todo> {
		let todo = { body: body };

		return this.http.post(this.apiUrl + '/todos', todo, this.getHeaders())
			.toPromise()
			.then(response => response.json() as Todo)
			.catch(this.onError);
	}

	patch(id: number, body: string = null, done: boolean = null): Promise<Todo> {
		let patch: any = { }
		if (body !== null) {
			patch.body = body;
		}
		if (done !== null) {
			patch.done = done;
		}

		return this.http.patch(this.apiUrl + '/todos/' + id, patch, this.getHeaders())
			.toPromise()
			.then(response => response.json() as Todo)
			.catch(this.onError);
	}

	delete(id: number): Promise<any> {
		return this.http.delete(this.apiUrl + '/todos/' + id, this.getHeaders())
			.toPromise()
			.catch(this.onError);
	}

	private getHeaders(): any {
		let creds = JSON.parse(localStorage.getItem('credentials')) as Credentials;
		let headers = new Headers();
		headers.append('Authorization', 'Basic ' + btoa(creds.username + ':' + creds.password));

		return { headers: headers };
	}

	private onError(error: any): Promise<any> {
		console.error('An error occurred', error);
		return Promise.reject(error.message || error);
	}
}
