import "./App.css";
import api from "./api/axiosConfig";
import { useState, useEffect } from "react";
import Layout from "./components/Layout";
import { createBrowserRouter ,RouterProvider} from "react-router-dom";
import Home from "./components/home/Home";
import Trailer from "./components/trailer/Trailer";
import Reviews from "./components/reviews/Reviews";
import NotFound from "./components/notFound/NotFound";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login/Login";
import Register from "./components/Login/Register";

function App() {
  const [movies, setMovies] = useState();
  const [movie, setMovie] = useState();
  const [reviews, setReviews] = useState([]);

  const getMovies = async () => {
    try {
      const response = await api.get("/api/v1/movies");
      setMovies(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const getMovieData = async (movieId) => {
    try {
      const response = await api.get(`/api/v1/movies/${movieId}`);
      const singleMovie = response.data;
      setMovie(singleMovie)
      setReviews(singleMovie.reviewIds);
    } catch (error) {
      console.error(error);
    }
  };


  
  const routes = [
    {
      path: "/",
      element: <Layout />,
      children: [
        {
          path: "/",
          element: <Home movies={movies} />,
        },
        {
          path: "/Trailer/:ytTrailerId",
          element: <Trailer />,
        },
        {
          path: "/Reviews/:movieId",
          element: (
            <Reviews
              getMovieData={getMovieData}
              movie={movie}
              reviews={reviews}
              setReviews={setReviews}
            />
          ),
        },
        {
          path: "/login",
          element: <Login />,
        },
        {
          path: "/register",
          element: <Register />,
        },
        {
          path: "*",
          element: <NotFound />,
        },
      ],
    },
  ];

  const Router = createBrowserRouter(routes);



  useEffect(() => {
    getMovies();
  }, []);

  return (
    <div className="App">
     <RouterProvider router={Router}></RouterProvider>
    </div>
  );
}

export default App;
