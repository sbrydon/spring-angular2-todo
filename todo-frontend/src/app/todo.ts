export class Todo {
	id: number;
	body: string;
	done: boolean;
	createdAt: Date;
	modifiedAt: Date;
	doneAt: Date;
}

export class GetAllTodosResult {
	content: Todo[];
	page: TodoPage;
}

class TodoPage {
	size: number;
	totalElements: number;
	totalPages: number;
	number: number;
}
