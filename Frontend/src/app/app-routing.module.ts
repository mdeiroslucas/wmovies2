import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { MovieDetailComponent } from './components/movie/movie-detail/movie-detail.component';
import { MovieResolver } from './components/movie/guards/movie.resolver';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'wmovies'},
  { path: 'wmovies', component: HomeComponent },
  { path: 'wmovies/:id', component: MovieDetailComponent},
  { path: 'search/:movieName', component: HomeComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
