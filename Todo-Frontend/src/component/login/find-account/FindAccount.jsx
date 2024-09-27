import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../../AxiosInterceptor";

const FindAccount = () => {
    const nav = useNavigate();
    const [id, setId] = useState('');
    const [email,setEmail] = useState('');
    const [securityNumber, setSecurityNumber] = useState('');

    const [confirm, setConfirm] = useState(false);
    //인증 번호 전송 버튼
    const findAccount = async () => {
        //id와 email이 일치하는 계정이 있는지 확인 후 인증번호 전송
        const res = await api.post(`/account`,{userId: id,userEmail:email});
        
        if(res.data){
            setConfirm(true);
        } else {
            alert('아이디랑 이메일이 일치하지 않아요~');
        }
    }
    const confirmSecurityNumber = async () => {
        const res = await api.get(`/account-sc-num?scNumber=${securityNumber}`);
        if(res.data){
            nav('/find-account/password-change');
        } else {
            alert('인증번호가 일치하지 않아요~');
        }
    }
    return(
        <section className="login-container">
            <div className="login-input-container">
                <div className="login-input">
                    <h1>비밀번호 찾기</h1>
                    <input className="login-id" placeholder="아이디를 입력해주세요" value={id} onChange={(e)=>setId(e.target.value)}/>
                    <input className="login-id" placeholder="이메일을 입력해주세요" value={email} onChange={(e)=>setEmail(e.target.value)}/>
                    
                    {confirm ? 
                    <>
                        <input value={securityNumber} onChange={(e)=>setSecurityNumber(e.target.value)} className="login-id" placeholder="인증번호를 입력해주세요"/>
                        <button onClick={confirmSecurityNumber} className="login-button">인증번호 확인</button>
                    </> : 
                    <button onClick={findAccount} className="login-button">인증번호 전송</button>
                    }
                </div>
            </div>
        </section>
    )
}

export default FindAccount;