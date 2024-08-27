import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environement } from 'src/Environnement/environement';
import { Activity } from '../model/Activity';
import { Rating } from '../model/Rating';
import { AuthService } from './auth.service'; // Import AuthService

@Injectable({
  providedIn: 'root',
})
export class ActivityService {
  private baseUrl = environement.baseurl + '/api/activities';

  constructor(private http: HttpClient, private authService: AuthService) {} // Inject AuthService

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // or use this.authService.getToken() if you expose a method in AuthService
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  public addactivity(activitydata: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, activitydata, {
      headers: this.getAuthHeaders(),
    });
  }

  getAllactivity(): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${this.baseUrl}`, {
      headers: this.getAuthHeaders(),
    });
  }

  getactivityByID(id: number): Observable<Activity> {
    return this.http.get<Activity>(`${this.baseUrl}/${id}`, {
      headers: this.getAuthHeaders(),
    });
  }

  public updateactivity(id: number, activitydata: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, activitydata, {
      headers: this.getAuthHeaders(),
    });
  }

  deleteactivity(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, {
      headers: this.getAuthHeaders(),
    });
  }

  submitRating(
    activityId: number,
    rating: Rating,
    userId: number
  ): Observable<Rating> {
    const params = new HttpParams().set('userId', userId.toString());
    return this.http.post<Rating>(
      `${this.baseUrl}/${activityId}/ratings`,
      rating,
      { params, headers: this.getAuthHeaders() }
    );
  }

  getRatingsByActivityId(activityId: number): Observable<Rating[]> {
    return this.http.get<Rating[]>(`${this.baseUrl}/${activityId}/ratings`, {
      headers: this.getAuthHeaders(),
    });
  }
}
