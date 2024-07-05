/* eslint-disable react/prop-types */
import { FastForward, Pencil, Trash } from "react-bootstrap-icons";
import { Suspense, useEffect, useRef, useState } from "react";
import api from "../../api/axiosConfig";
import { useNavigate, useParams } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
import ReviewForm from "../reviewForm/ReviewForm";
import { useUserContext } from "../../Context/UserContext";
import EditModal from "../EditModal/EditModal";
import { MDBSpinner, MDBBtn } from "mdb-react-ui-kit";
import Loader from "../Loader";

const Reviews = ({ getMovieData, movie, reviews, setReviews }) => {
  const [loading,setloading] = useState(true)
  const [show, setShow] = useState(false);
  const [content, setContent] = useState("");
  const [imdbId, setid] = useState("");
  const nav = useNavigate();
  const { user, dispatch } = useUserContext();
  const revText = useRef();
  let params = useParams();
  const movieId = params.movieId;
  const [isreviewed, setre] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        await getMovieData(movieId);
        setloading(false);
      } catch (error) {
        console.error(error);
      }
    };
  
    fetchData();
  }, []);
useEffect(()=>{
  handleupdateIsReviewed()
},[reviews])



  const handleupdateIsReviewed = () => {
    setre((re) =>
      reviews.some(
        (l) => l.username === JSON.parse(localStorage.getItem("user"))?.username
      )
    );
  };

  const handleDelete = async (imdbId, body) => {
    console.log({ imdbId, body });
    if (!user.user) {
      nav("/login");
      return;
    }
    try {
      const response = await api.post(
        "/review/delete",
        { imdbId: imdbId, body: body },
        {
          headers: {
            Authorization: `Bearer ${user.user.token}`,
          },
        }
      );

      const updatedReviews = reviews.filter((l) => l.body !== body);

      setReviews(updatedReviews);
    } catch (err) {
      console.error(err);
    }
  };

  const addReview = async (e) => {
    e.preventDefault();

    if (!user.user) {
      nav("/login");
      return;
    }
    const rev = revText.current;

    try {
      const response = await api.post(
        "/review/create",
        { imdbId: movieId, body: rev.value },
        {
          headers: {
            Authorization: `Bearer ${user.user.token}`,
          },
        }
      );

      const updatedReviews = [
        ...reviews,
        { body: rev.value, username: user.user.username, imdbId: movieId },
      ];

      rev.value = "";

      setReviews(updatedReviews);
    } catch (err) {
      console.error(err);
    }
  };

  if(loading){
    return <Loader/>  
  }
  return (
    <Container style={{overflow:"auto",backgroundColor:"black",width:"100%",minHeight:"93.9dvh"}} >
      <Row className="mt-2">
        <Col>
          <img src={movie?.poster} alt="" width={300} />
        </Col>
        <Row>
          <Col>
            <h3>Reviews</h3>
          </Col>
        </Row>
        <Col>
        
            {!isreviewed && (
              <>
                <Row>
                  <Col>
                    <ReviewForm
                      handleSubmit={addReview}
                      revText={revText}
                      labelText="Write a Review?"
                      isedit={false}
                    />
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <hr />
                  </Col>
                </Row>
              </>
            )}

          {reviews?.map((r, index) => (
            <div
              key={index}
              className="card mb-4 custom-card"
              style={{ background: "#333", color: "#fff" }}
            >
              <div className="card-header custom-card-header d-flex justify-content-between align-items-center">
                <strong>{r.username}</strong>
                {user.user.username === r.username && (
                  <div className="btn-group p-1" role="group">
                    <Pencil
                      onClick={() => {
                        setContent(r.body);
                        setid(r.imdbId);
                        setShow(true);
                      }}
                    />
                    &nbsp;
                    <Trash onClick={() => handleDelete(r.imdbId, r.body)} />
                  </div>
                )}
              </div>
              <div className="card-body custom-card-body">{r.body}</div>
            </div>
          ))}

          <EditModal
            setShow={setShow}
            show={show}
            content={content}
            setcontent={setContent}
            setid={setid}
            imdbId={imdbId}
          />
        </Col>
      </Row>
      <Row>
        <Col>
          <hr />
        </Col>
      </Row>
    </Container>
  );
};

export default Reviews;
