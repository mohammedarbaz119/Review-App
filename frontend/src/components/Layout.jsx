import { Outlet } from "react-router-dom";
import Header from "./header/Header";


const Layout = () => {
  return (
    <>
    <Header></Header>
    <main style={{height:"fit-content"}}>
      <Outlet/>
    </main>
    </>
  )
}

export default Layout
