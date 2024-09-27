import './App.css';
import Todo from './component/todo/Todo';
import Join from './component/join/Join';
import Login from './component/login/Login';
import MyPage from './component/mypage/MyPage';
import Chat from './component/chat/Chat';
import NotFound from './component/error/NotFound';

import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Provider, useDispatch, useSelector} from 'react-redux';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMoon,faSun } from "@fortawesome/free-solid-svg-icons";
import { toggleDarkMode } from './redux/ThemeActions';
import FindAccount from './component/login/find-account/FindAccount';
import PasswordChange from './component/login/find-account/PasswordChange';


function App() {
  const darkMode = useSelector((state) => state.theme.darkMode);
  const dispatch = useDispatch();
  return(
    <div style={{backgroundColor: darkMode? '#1C1C1C' : '#fff'}} className="container">
      <div className='toggle-theme' onClick={()=>dispatch(toggleDarkMode())}>
      {darkMode ? <FontAwesomeIcon icon={faSun} size='2xl' style={{color: "#829efb75"}} /> : <FontAwesomeIcon icon={faMoon} size='2xl'/>}
      </div>
      {/* <button onClick={}>{darkMode ? 'Light Mode' : 'Dark Mode'}</button> */}
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login />} />
        <Route path='/join' element={<Join />} />
        <Route path='/todo' element={<Todo />} />
        <Route path='/mypage' element={<MyPage/>}/>
        <Route path='/chat' element={<Chat/>}/>
        <Route path='/find-account' element={<FindAccount/>}/>
        <Route path='/find-account/password-change' element={<PasswordChange/>}/>
        <Route path='*' element={<NotFound/>}/>
      </Routes>
    </BrowserRouter>
    </div>
  );
}

export default App;
