import { Component } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Movies } from 'src/app/model/movies';
import { MovieService } from 'src/app/service/movie.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  movies$: Observable<Movies[]>;

  constructor (
    private moviesService: MovieService
  ) {
    this.movies$ = this.moviesService.getMovies();
  }


}
