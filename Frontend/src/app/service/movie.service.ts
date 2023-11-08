import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Movies } from '../model/movies';
import { catchError, first, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class MovieService {
  
  private readonly API = "wmovies/now";
  
  constructor(
    private httpClient: HttpClient
    ) {}

  getMovies() {
    // return [ {id:"2", title: "oi", overview: "Vivendo" }];
    console.log("chamou");
    return this.httpClient.get<Movies[]>(this.API)
      .pipe(
        first(),
        catchError(error => {
          console.log("deu ruim na hora de chamar filmes");
          return of([]);
        })
      );
  }
}
