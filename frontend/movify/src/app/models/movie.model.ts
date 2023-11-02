export interface Movie {
  id: number;
  title: string;
  genres: Genre[];
  runtime: number;
  overview: string;
  posterPath: string;
  releaseYear: string;
  trailer: string;
  cast: Cast[];
}

interface Genre {
  name: string;
}

interface Cast {
  character: string;
  name: string;
  profilePhoto: string;
}
