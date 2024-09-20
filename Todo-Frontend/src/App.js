import './App.css';
import Todo from './component/todo/Todo';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Provider, useDispatch, useSelector} from 'react-redux';
import Join from './component/join/Join';
import Login from './component/login/Login';
import MyPage from './component/mypage/MyPage';
import { useState } from 'react';
import store from './store';
import { toggleDarkMode } from './redux/ThemeActions';


function App() {
  const darkMode = useSelector((state) => state.theme.darkMode);
  const dispatch = useDispatch();
  return(
    <div style={{
      backgroundColor: darkMode? '#1C1C1C' : '#fff'
    }} className="container">
      <button onClick={()=>dispatch(toggleDarkMode())}>{darkMode ? 'Light Mode' : 'Dark Mode'}</button>
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='/join' element={<Join />} />
        <Route path='/todo' element={<Todo />} />
        <Route path='/mypage' element={<MyPage/>}/>
      </Routes>
    </BrowserRouter>
    </div>
  );
}

export default App;
