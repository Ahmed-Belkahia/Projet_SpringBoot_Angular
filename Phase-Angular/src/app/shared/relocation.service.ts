import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { Relocation } from 'src/app/modele/relocation.module';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RelocationService {
  readonly  API_URL = 'http://localhost:8000/PI/api/relocations';

  constructor(private httpClient: HttpClient) { }

  getAllRelocation() {
    return this.httpClient.get(`${this.API_URL}`)
  }
  addRelocatio(relocation: Relocation, userId: number): Observable<number> {
    return this.httpClient.post<number>(`${this.API_URL}/${userId}`, relocation);
  }
  getRelocationById(id :any){
    console.log('gg' , id)
    return this.httpClient.get('http://localhost:8000/PI/api/relocations/'+id)
  }

  getRelocationById1(id: any): Observable<Relocation> {
    return this.httpClient.get<Relocation>('http://localhost:8000/PI/api/relocations/'+id);
  }

  
  async getRelocationById2(id: any): Promise<Relocation> {
    const relocation = await this.httpClient.get<Relocation>('http://localhost:8000/PI/api/relocations/'+id).toPromise();
    return relocation || {} as Relocation;
  }



  EditRelocation(relocationid : any,  data:any){
    return this.httpClient.put('http://localhost:8000/PI/api/relocations/'+ relocationid,data)
  }
  deleteRelocation(idProduct : any){
    return  this.httpClient.delete(`${this.API_URL}/${idProduct}`, { responseType: 'text' });
  }
  
  

}
