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
  movies$: Observable<Movies[]>;

  constructor (
    private moviesService: MovieService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.movies$ = this.moviesService.getMovies();
  }

  getById(id: string){
    console.log("O id selecionado foi: " + id);

    this.router.navigate(['wmovies/' + id], {relativeTo: this.route});
  }


}
