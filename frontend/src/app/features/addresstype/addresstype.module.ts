import { RouterModule, Routes } from '@angular/router';
import { AddresstypeListComponent } from './components/addresstype-list/addresstype-list.component';
import { AddresstypeFormComponent } from './components/addresstype-form/addresstype-form.component';
import { AddresstypeDetailComponent } from './components/addresstype-detail/addresstype-detail.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddressTypeService } from './services/addresstype.service';

const routes: Routes = [
    {path: '', component: AddresstypeListComponent},
    {path: 'new', component: AddresstypeFormComponent},
    {path: ':id', component: AddresstypeDetailComponent},
    {path: ':id/edit', component: AddresstypeFormComponent}
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        AddresstypeListComponent,
        AddresstypeFormComponent,
        AddresstypeDetailComponent
    ],
    providers: [AddressTypeService]
})
export class AddressTypeModule {}