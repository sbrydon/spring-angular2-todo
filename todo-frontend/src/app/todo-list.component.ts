import { Component, OnInit } from '@angular/core';

import { TodoService } from './todo.service';
import { GetAllTodosResult, Todo } from './todo';
import { TodoHeader } from './todo-header.component';
import { Page } from './pager.component';
import { SaveEvent, DeleteEvent } from './todo-list-item.component';

@Component({
	selector: 'my-todo-list',
	templateUrl: './todo-list.component.html',
})
export class TodoListComponent implements OnInit {
	header = new TodoHeader();
	todos: Todo[];
	page = new Page();

	constructor(private todoService: TodoService) { }

	ngOnInit(): void {
		this.refreshTodos();
	}

	refreshTodos(): void {
		let done;

		switch (this.header.filterOn) {
			case 'all':
				done = null;
				break;
			case 'active':
				done = false;
				break;
			case 'completed':
				done = true;
				break;
			default:
				console.error('getTodosForCurrentPage: unexpected filterOn');
				return;
		}

		this.todoService.getAll(this.page.number - 1, done)
			.then(result => this.onGetAllSuccess(result));
	}

	private onGetAllSuccess(result: GetAllTodosResult): void {
		this.todos = result.content;
		this.page.collectionSize = result.page.totalElements;
		this.page.size = result.page.size;
	}

	onFilterChange(): void {
		this.refreshTodos();
	}

	onSubmit(): void {
		this.todoService.post(this.header.newTodo)
			.then(todo => this.onPostSuccess(todo));
	}

	private onPostSuccess(todo: Todo): void {
		this.header.resetFilterOn();
		this.page.moveToFirst();
	}

	onPageChange(): void {
		this.refreshTodos();
	}

	onSaveField(e: SaveEvent): void {
		switch (e.field) {
			case 'body':
				this.todoService.patch(e.todo.id, e.todo.body, null)
					.then(updated => this.onPatchSuccess(e, updated));
				break;
			case 'done':
				this.todoService.patch(e.todo.id, null, e.todo.done)
					.then(updated => this.onPatchSuccess(e, updated));
				break;
			default:
				console.error('onSaveField: unexpected field');
				break;
		}
	}

	private onPatchSuccess(e: SaveEvent, updated: Todo): void {
		e.todo.modifiedAt = updated.modifiedAt;
		e.todo.doneAt = updated.doneAt;
	}

	onDelete(e: DeleteEvent): void {
		this.todoService.delete(e.todo.id)
			.then(() => this.onDeleteSuccess());
	}

	private onDeleteSuccess(): void {
		this.refreshTodos();
	}
}
