import './App.css';
import Todo from './component/todo/Todo';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Join from './component/join/Join';
import Login from './component/login/Login';
import MyPage from './component/mypage/MyPage';


function App() {
  return(
    <div className="container">
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
