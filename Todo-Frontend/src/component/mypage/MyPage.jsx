import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"
import './MyPage.css';
import api from "../../AxiosInterceptor";
import BackButton from "../common/BackButton";
import { getUserProfileImage, setStyle } from "../common/CommonFunction";
import ProfileImage from "../common/ProfileImage";
import { useSelector } from "react-redux";

const MyPage = () => {
    const nav = useNavigate();
    const darkMode = useSelector(state => state.theme.darkMode);
    const [user,setUser] = useState({});    //나중에 redux로 관리하기
    const [nickname, setNickname] = useState('');
    const [pw,setPw] = useState('');
    const [pw2, setPw2] = useState('');
    const [isPwMatch, setIsPwMatch] = useState(false);
    const [confirmPwMessage, setConfirmPwMessage] = useState('');
    const modifyPassword = async () => {
        const res = await api.post('/password',{userPw:pw});
        localStorage.removeItem('token');
        nav('/',{state: {message: res.data}});
    }
    const checkPwMatch = () =>{
        //setting
        const minPwLength = 7;

        if(pw.length > minPwLength){
            if(pw===pw2){
                setIsPwMatch(true);
            } else {
                setConfirmPwMessage('비밀번호가 일치하지 않습니다.');
                setIsPwMatch(false);
            }
        } else {
            setConfirmPwMessage(`비밀번호는 ${minPwLength}자리 이상으로 해야합니다.`);
            setIsPwMatch(false);
        }
    }
    const modifyNickname = async () =>{
        try{
            const res = await api.post('/nickname',{
                userNickname: nickname,
            })
            nav('/',{state:{message:res.data}})            
        } catch(err){
            alert(err);
        }
    }
    const uploadFile = async (file) => {
        const res = await api.post('/file/upload',{file:file});
        window.location.reload();
    }
    useEffect(()=>{
        setUser(jwtDecode(localStorage.getItem('token')));

    },[])
    useEffect(()=>{
        if(pw.length<=0){
            setPw2('');
        } else {
            checkPwMatch();
        }
    },[pw])
    useEffect(()=>{
        checkPwMatch();
    },[pw2])
    useEffect(()=>{
        if(Object.keys(user).length > 0){
            setNickname(user.accountNickname);
        }
    },[user])
    return(
        <div className="todo-app">
            <div style={{background:setStyle(darkMode, 'container')}} className="mypage-container">
                <div className="mypage-header">
                    <BackButton moveTo='/todo'/>
                </div>
                <div>
                    <div className="profile-input-container">
                        <div style={{marginTop:20, display:'flex'}}>
                            <ProfileImage width={120} height={120}/>
                            <div style={{fontSize:25,alignSelf:'center',marginLeft:30,color:setStyle(darkMode,'text')}}>{user.accountNickname}</div>
                        </div>
                        <label htmlFor="file-upload" className="custom-file-upload">
                            프로필 사진 변경
                        </label>
                        <input
                            id="file-upload"
                            type="file"
                            className="profile-input"
                            onChange={(e)=>uploadFile(e.target.files[0])}
                        />
                    </div>

                </div>
                <div className="mypage-infor-container">
                <div className="mypage-text">
                    <div>회원 번호</div>
                    <div>아이디</div>
                    <div>닉네임</div>
                    <div>권한</div>
                    <div>비밀번호 변경</div>
                    {pw.length>0 ? <div>다시 입력</div> : <></>}
                </div>
                <div className="mypage-infor">
                    <div style={{color:setStyle(darkMode,'text')}}>{user.accountCode}</div>
                    <div style={{color:setStyle(darkMode,'text')}}>{user.accountId}</div>
                    <div>
                        <input value={nickname} onChange={(e)=>setNickname(e.target.value)}/>
                        <button className="pw-change" onClick={modifyNickname}>변경</button>
                    </div>
                    <div>{user.accountRole==='ROLE_USER'?<div style={{color:setStyle(darkMode,'text')}}>일반 유저</div> : <div style={{color:setStyle(darkMode,'text')}}>관리자</div>}</div>
                    <input type="password" value={pw} onChange={(e)=>setPw(e.target.value)} style={{marginTop:-10,marginBottom:10}}/>

                    {
                            pw.length > 0 ? 
                            <div style={{display:'flex'}}>
                                <input type="password" value={pw2} onChange={(e)=>setPw2(e.target.value)}/>
                                {isPwMatch ? <button className="pw-change" onClick={modifyPassword}>변경하기</button> : <div style={{color:setStyle(darkMode,'text')}} className="pw-change-text">{confirmPwMessage}</div>}
                            </div> : <></>
                        }
                
                </div>
            </div>
            </div>
            
        </div>
    )
}

export default MyPage;