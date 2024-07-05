/* eslint-disable react/prop-types */
import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import ReviewForm from "../reviewForm/ReviewForm";
import axiosConfig from "../../api/axiosConfig";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../../Context/UserContext";

export default function EditModal({ show, setShow,content,setcontent,imdbId,setid}) {
  const nav = useNavigate()
  const {user}=  useUserContext()
  const handleClose = () => {
    setcontent("")
    setid("")
    setShow(false)
    console.log(content)
  };
  const handleShow = () => {
    setShow(true)
  };
  const submit = async (e) => {
    e.preventDefault();

    if (!user.user) {
      nav("/login");
      return;
    }

    try {
      const response = await axiosConfig.post(
        "/review/edit",
        {  imdbId: imdbId,body: content },
        {
          headers: {
            Authorization: `Bearer ${user.user.token}`,
          },
        }
      );

    handleClose()
    window.location.reload()
    } catch (err) {
      console.error(err);
    }
  };

  
  return (
    <>
      <Modal
        show={show}
        onHide={handleClose}
        backdrop="static"
        keyboard={false}
        size="lg"
        
      >
        <Modal.Header closeButton style={{backgroundColor:'grey'}}>
          <Modal.Title className="text-black">Modal title</Modal.Title>
        </Modal.Header>
        <Modal.Body className="bg-black text-white">
        <ReviewForm isedit={true} defaultValue={content} handleSubmit={submit} setcontent={setcontent}/>
      </Modal.Body>
      </Modal>
    </>
  );
}
