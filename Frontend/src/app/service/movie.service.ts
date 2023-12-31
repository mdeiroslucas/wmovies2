import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movies } from '../model/movies';
import { Observable, catchError, first, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private readonly API = 'http://localhost:8080/wmovies/';

  constructor(private httpClient: HttpClient) {}

  getMovies() {
    return this.httpClient.get<Movies[]>(this.API + 'now').pipe(
      tap((movies) => console.log(movies)),
      catchError((error) => {
        console.log('deu ruim na hora de chamar filmes');
        return of([]);
      })
    );
  }

  getMovieById(id: string): Observable<Movies> {
    return this.httpClient.get<Movies>(this.API + id).pipe(
      catchError((error) => {
        console.log('deu ruim na hora de chamar filmes');
        return of();
      })
    );
  }

  getMoviesbySearch(movieName: string) {
    return this.httpClient.get<Movies[]>(this.API + 'search/' + movieName).pipe(
      tap((movie) => console.log(movie)),
      catchError((error) => {
        console.log('deu ruim na hora de chamar filmes');
        return of([]);
      })
    );
  }

  getSimilarMovies(movieId: string) {
    return this.httpClient.get<Movies[]>(this.API + 'similar/' + movieId).pipe(
      tap((movie) => console.log(movie)),
      catchError((error) => {
        console.log('deu ruim na hora de chamar o ai de filmes');
        return of([]);
      })
    );
  }
}
