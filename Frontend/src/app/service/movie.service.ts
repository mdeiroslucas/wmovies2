import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movies } from '../model/movies';
import { catchError, first, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class MovieService {
  
  private readonly API = "wmovies";
  
  constructor(
    private httpClient: HttpClient
    
    ) {
      console.log('chamou construtor');
    }

  getMovies() {
    return this.httpClient.get<Movies[]>(this.API+ '/now')
      .pipe(
        tap(movies => console.log(movies)),
        catchError(error => {
          console.log("deu ruim na hora de chamar filmes");
          return of([]);
        })
      );
  }
}