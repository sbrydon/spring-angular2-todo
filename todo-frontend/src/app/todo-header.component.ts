import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
	selector: 'my-todo-header',
	templateUrl: './todo-header.component.html'
})
export class TodoHeaderComponent {
	@Input() header: TodoHeader;
	@Output() onFilterChange = new EventEmitter<void>();
	@Output() onSubmit = new EventEmitter<void>();

	filter(on: string): void {
		this.header.filterOn = on;
		this.onFilterChange.emit();
	}

	submit(): void {
		this.onSubmit.emit();
		this.header.newTodo = '';
	}
}

export class TodoHeader {
	filterOn = 'all';
	newTodo = '';

	resetFilterOn(): void {
		this.filterOn = 'all';
	}
}
