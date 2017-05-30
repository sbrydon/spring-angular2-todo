import { Component, EventEmitter, Input, Output } from '@angular/core';

import { Todo } from './todo';

@Component({
	selector: 'my-todo-list-item',
	templateUrl: './todo-list-item.component.html'
})
export class TodoListItemComponent {
	@Input() todo: Todo;
	@Output() onSave = new EventEmitter<SaveEvent>();
	@Output() onDelete = new EventEmitter<DeleteEvent>();

	save(field: string): void {
		this.onSave.emit(new SaveEvent(this.todo, field));
	}

	delete(): void {
		this.onDelete.emit(new DeleteEvent(this.todo));
	}
}

export class SaveEvent {
	constructor(public todo: Todo, public field: string) { }
}

export class DeleteEvent {
	constructor(public todo: Todo) { }
}
