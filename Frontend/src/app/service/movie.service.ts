import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movies } from '../model/movies';
import { Observable, catchError, first, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class MovieService {
  
  private readonly API = "http://localhost:8080/wmovies/";
  
  constructor(
    private httpClient: HttpClient
    
    ) {
      console.log('chamou construtor');
    }

  getMovies() {
    console.log('chamou a api');
    return this.httpClient.get<Movies[]>(this.API + 'now')
      .pipe(
        tap(movies => console.log(movies)),
        catchError(error => {
          console.log("deu ruim na hora de chamar filmes");
          return of([]);
        })
      );
  }

  // getMovieById(id: string)  {
  //   return this.httpClient.get<Movies>(this.API + id)
  //   .pipe(
  //     tap(movie => console.log(movie)),
  //     catchError(error => {
  //       console.log("deu ruim na hora de chamar o filme pelo id");
  //       return of([]);
  //     })
  //   );
  // }

  getMovieById(id: string): Observable<Movies> {
    return this.httpClient.get<Movies>(this.API + id)
      .pipe(
        catchError(error => {
          console.log("deu ruim na hora de chamar filmes");
          return of();
        })
      );
  }

  getMoviesbySearch(movieName: string) {
    console.log('chegou aqui')
    return this.httpClient.get<Movies[]>(this.API +'search/'+ movieName)
      .pipe(
        tap(movie => console.log(movie)),
        catchError(error => {
          console.log("deu ruim na hora de chamar filmes");
          return of([]);
        })
      );
  }      

}
