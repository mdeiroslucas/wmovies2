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
  // movies$: Movies[] = [
  //   { id: "1", title: 'Movie 1', overview: 'movie description' },
  //   { id: "2", title: 'Movie 2', overview: 'movie description 2' }
  // ];

  constructor (
    private moviesService: MovieService
  ) {
    this.movies$ = this.moviesService.getMovies();


    this.movies$.pipe(
      tap(movies => {
        movies.forEach(movie => {
          console.log(movie.backDropPath);
        });
      })
    ).subscribe();

    console.log("oi");
  }


}
