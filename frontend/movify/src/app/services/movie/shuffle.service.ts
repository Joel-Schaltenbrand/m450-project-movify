import { Injectable } from "@angular/core";
import axios from "axios";
import { MOVIE_BASE_URL } from "src/app/utils";
import { Movie } from "src/app/models/movie.model";

@Injectable({
  providedIn: "root",
})
export class ShuffleService {
  constructor() {}

  async getMovie(): Promise<Movie> {
    return new Promise(async (resolve, reject) => {
      try {
        const response = await axios.get(`${MOVIE_BASE_URL}/getRandomMovie`, {
          withCredentials: true,
        });
        resolve(response.data);
      } catch (error) {
        reject(error);
      }
    });
  }
}
