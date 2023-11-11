import 'bootstrap/dist/css/bootstrap.min.css';

const Loader = () => {
  return (
    <div className="loader-overlay d-flex justify-content-center align-items-center">
      <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  );
};

export default Loader;
