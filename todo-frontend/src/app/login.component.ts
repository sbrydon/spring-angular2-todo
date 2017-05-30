import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';
import { Credentials } from './credentials';

@Component({
	selector: 'my-login',
	templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
	credentials: Credentials = new Credentials();

	constructor(private router: Router, private authService: AuthService) { }

	ngOnInit(): void {
		this.authService.logout();
	}

	login(): void {
		this.authService.login(this.credentials)
			.then(() => this.router.navigate(['/todos']));
	}
}
