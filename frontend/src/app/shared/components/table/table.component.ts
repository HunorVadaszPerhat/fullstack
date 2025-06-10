import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-table',
  imports: [],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})
export class TableComponent {
  @Input() columnHeadNameArray: string[] = [];
  @Input() objectFieldNameArray: string[] = [];
  @Input() gridData : any[] = [];
  item: any;

  isEdit : boolean = false;

  @Output() onEditClicked = new EventEmitter<any>();
  @Output() onDeleteClicked = new EventEmitter<any>();

  onEditBtnClicked(item: any) {
    this.onEditClicked.emit(item);
  }

  onDeleteBtnClicked(item: any) {
    this.onDeleteClicked.emit(item);
  }

}
