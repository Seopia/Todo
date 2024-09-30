import { useState } from "react";
import { useNavigate } from "react-router-dom";

const PasswordChange = () => {
    const nav = useNavigate();
    const [pw, setPw] = useState('');
    const [pw2, setPw2] = useState('');
    const [msg, setMsg] = useState('');
    const changePassword = () => {
        if(pw === pw2){
            const res = {data : '비밀번호가 변경되었습니다. 다시 로그인해주세요.'}
            nav('/',{state:{message:res.data}})   
        } else {
            setMsg('비밀번호가 일치하지 않아요.');
        }

    }
    return(
        <section className="login-container">
        <div className="login-input-container">
            <div className="login-input">
                <h1>비밀번호 바꾸기</h1>
                {msg ? <div style={{color:'red'}}>{msg}</div> : <></>}
                <input className="login-id" placeholder="비밀번호를 입력해주세요" value={pw} onChange={(e)=>setPw(e.target.value)}/>
                <input className="login-id" placeholder="다시 한 번 입력해주세요" value={pw2} onChange={(e)=>setPw2(e.target.value)}/>
                <button onClick={changePassword} className="login-button">비밀번호 변경</button>
            </div>
        </div>
    </section>
    )
}

export default PasswordChange;