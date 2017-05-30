import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
	selector: 'my-pager',
	templateUrl: './pager.component.html'
})
export class PagerComponent {
	private _page: Page;

	@Input() set page(page: Page) {
		this._page = page;
		this._page.onNumberChange = () => this.pageChange();
	}

	get page() {
		return this._page;
	}
	
	@Output() onPageChange = new EventEmitter<void>();

	pageChange(): void {
		this.onPageChange.emit();
	}
}

export class Page {
	collectionSize = 0;
	size = 0;
	number = 1;

	onNumberChange: () => void;

	moveToFirst(): void {
		this.number = 1;
		this.onNumberChange();
	}
}
