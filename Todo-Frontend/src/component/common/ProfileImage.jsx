import { useEffect, useState } from "react";
import { getUserProfileImage } from "./CommonFunction";
import './CommonCSS.css'
import { useNavigate } from "react-router-dom";

const ProfileImage = ({width,height}) => {
    const nav = useNavigate();
    const [profileImg, setProfileImg] = useState(null);

    useEffect(()=>{
        getUserProfileImage()
        .then(res => {
            setProfileImg(res.data);
        })
    },[])

    return(
        <div onClick={()=>{nav('/mypage')}} style={{
            cursor:'pointer',
            width: width,
            height: height,
        }} className="mypage-profile-image">
            {profileImg!=='http://localhost:8080/uploads/null' ? 
            <img src={profileImg} alt="file"/> 
            : 
            <img src="/profile/default-profile.jpg" alt="엥?"/>}
        </div>
    )
}

export default ProfileImage;