/* eslint-disable react/prop-types */
import React, { createContext, useContext, useEffect, useReducer} from "react";

const MyContext = createContext();

const initialState = {
  user: null,
};

export const useUserContext = () => {
  const context = useContext(MyContext);
  if (!context) {
    throw new Error("useMyContext must be used within a MyContextProvider");
  }
  return context;
};

const Usercontext = ({ children }) => {
  const [user, dispatch] = useReducer((state, action) => {
    switch (action.type) {
      case "LOGIN":
        localStorage.setItem("user", JSON.stringify(action.payload));
        return { ...state, user: action.payload };
      case "LOGOUT":
        localStorage.setItem("user", null);
        return { ...state, user: null };
      default:
        return state;
    }
  }, initialState);
  useEffect(() => {
    // Load user state from localStorage
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      dispatch({ type: 'LOGIN', payload: JSON.parse(savedUser) });
    }
  }, []);

  return (
    <MyContext.Provider value={{ user, dispatch }}>
      {children}
    </MyContext.Provider>
  );
};
export default Usercontext;