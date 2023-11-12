import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  ResolveFn,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Movies } from 'src/app/model/movies';
import { MovieService } from 'src/app/service/movie.service';

@Injectable({
  providedIn: 'root',
})
export class MovieResolver implements Resolve<Movies> {
  constructor(private moviesService: MovieService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Movies> {
    if (route.params && route.params['id']) {
      return this.moviesService.getMovieById(route.params['id']);
    }
    return of({
      id: '',
      title: '',
      backDropPath: '',
      overview: '',
      voteAverage: 0,
      posterPath: '',
    });
  }
}
