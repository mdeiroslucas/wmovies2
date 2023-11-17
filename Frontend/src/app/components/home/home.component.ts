import { Component } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Movies } from 'src/app/model/movies';
import { MovieService } from 'src/app/service/movie.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  movies$!: Observable<Movies[]>;

  constructor (
    private moviesService: MovieService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    activatedRoute.params.subscribe((params) => {
      if(params.movieName) {
        console.log('chamo o filme por parametro')
        this.movies$ = this.moviesService.getMoviesbySearch(params.movieName);
      }
      else 
        this.movies$ = this.moviesService.getMovies();
      
    })
  }

  getById(movie: Movies){
    this.router.navigate([movie.id], {relativeTo: this.activatedRoute});
  }

  getMoviesbySearch(movieName: string){
    this.router.navigate(['/search/'+ movieName], {relativeTo: this.activatedRoute});
  }


}
