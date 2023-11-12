import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { MovieDetailComponent } from './components/movie/movie-detail/movie-detail.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'wmovies'},
  { path: 'wmovies', component: HomeComponent },
  { path: 'wmoves/{id}', component: MovieDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
