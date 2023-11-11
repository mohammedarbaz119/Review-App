import {Form,Button, Col, Row} from 'react-bootstrap';

const ReviewForm = ({handleSubmit,revText,labelText,defaultValue,isedit,setcontent}) => {
  return (
    <Form>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
            <Form.Label>{labelText}</Form.Label>
            {isedit?<Form.Control onChange={(e)=>setcontent(e.target.value)} as="textarea" rows={3} defaultValue={defaultValue} />:<Form.Control ref={revText} as="textarea" rows={3} defaultValue={defaultValue} />}
        </Form.Group>
        {isedit?<Row className="justify-content-md-center">
        <Col md="auto">
        <Button variant="outline-info" onClick={handleSubmit}>
            Submit
          </Button>
        </Col>
      </Row>:  <Button variant="outline-info" onClick={handleSubmit}>
            Submit
          </Button>}

    </Form>   

  )
}

export default ReviewForm
