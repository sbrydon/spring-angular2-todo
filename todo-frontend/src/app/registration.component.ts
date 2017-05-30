import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';
import { Credentials } from './credentials';

@Component({
	selector: 'my-registration',
	templateUrl: './registration.component.html'
})
export class RegistrationComponent {
	credentials: Credentials = new Credentials();

	constructor(private router: Router, private authService: AuthService) { }

	register(): void {
		this.authService.register(this.credentials)
			.then(() => this.onSuccess());
	}

	private onSuccess() {
		this.authService.login(this.credentials)
			.then(() => this.router.navigate(['/todos']));
	}
}
