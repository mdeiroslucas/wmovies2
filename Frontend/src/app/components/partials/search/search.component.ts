import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent {
  movieName = '';

  constructor(activatedRoute: ActivatedRoute, private router:Router){
    activatedRoute.params.subscribe((params) => {
      if(params.movieName) this.movieName = params.movieName;
    });
  }

  search(name: string): void {
    console.log('chamou no search');
    if (name) 
    this.router.navigateByUrl('/search/' + name);
  }

}
