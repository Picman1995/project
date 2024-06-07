import { useState } from 'react';
// import { Link } from 'react-router-dom';
import { loginUser } from '../../utils/auth';
import { PiUserFill } from 'react-icons/pi';
import { IoLockClosed } from 'react-icons/io5';
import '../LoginPage/index.css';
import {
  validateEmail,
  validatePassword,
} from '../../utils/validations/formValidation';
import useAuthContext from '../../hooks/useAuthContext';
import { EyeIcon, EyeSlashIcon } from '@heroicons/react/24/outline';
import Loading from '../../components/Loading';
import { Link } from 'react-router-dom';

const LoginPage = () => {
  const { login } = useAuthContext();

  const [username, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({
    username: '',
    password: '',
    login: '',
  });

  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false); //componente loading

  const handleChangeEmail = (e) => {
    const usernameValue = e.target.value;
    setEmail(usernameValue.toLowerCase());

    if (validateEmail(usernameValue)) {
      setErrors((prevErrors) => ({ ...prevErrors, username: '' }));
    } else if (usernameValue === '') {
      setErrors((prevErrors) => ({
        ...prevErrors,
        username: 'Este campo es requerido',
      }));
    } else {
      setErrors((prevErrors) => ({
        ...prevErrors,
        username: 'Ingresa un correo válido',
      }));
    }
  };

  const handleChangePassword = (e) => {
    const passwordValue = e.target.value;
    setPassword(passwordValue);

    if (validatePassword(passwordValue)) {
      setErrors((prevErrors) => ({ ...prevErrors, password: '' }));
    } else if (passwordValue === '') {
      setErrors((prevErrors) => ({
        ...prevErrors,
        password: 'Este campo es requerido',
      }));
    } else {
      setErrors((prevErrors) => ({
        ...prevErrors,
        password: 'Ingresa una contraseña válida',
      }));
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    if (errors.username || errors.password) {
      return;
    }
    setIsLoading(true);
    try {
      const { data } = await loginUser(username, password);
      setErrors((prevErrors) => ({ ...prevErrors, login: '' }));
      login(data.token);
    } catch (error) {
      <div className='flex lg:flex-1'>
      <Link to='/'>
        <h1 className='text-4xl font-semibold bg-blue-gradient text-transparent bg-clip-text'>
          <span>Stockwise</span>
          {/* <span className='hidden md:block'>Stockwise</span> */}
          {/* <span className='md:hidden'>SW</span> */}
        </h1>
      </Link>
    </div>
      // if (error.response && error.response.status === 403) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        login: 'Usuario o contraseña  inválidos.',
      }));
      // } else {
      //   setErrors((prevErrors) => ({
      //     ...prevErrors,
      //     login: 'Error inesperado, intentelo nuevamente.',
      //   }));
      // }
    } finally {
      setIsLoading(false);
    }
  };
  return (
    <>

      <div className='body'>

        <div className='login-form-container'>
          <div className="blob"></div>
          <div className="logo_login"></div>


          <div className="wrapper_login">
            <form
              onSubmit={handleLogin}
              className=''
            >


              <div>
                <div
                  className={`  ${errors.username ? '' : ''
                    }  ${errors.username
                      ? ''
                      : ''
                    } `}
                >
                  <div className="input-box">
                    <PiUserFill className="icon" />
                    <input
                      type='text'
                      className=''
                      value={username}
                      onChange={handleChangeEmail}
                      onBlur={handleChangeEmail}
                      required
                    />
                    <label>Usuario</label>
                  </div>
                </div>

                <div
                  className={`  ${errors.password ? '' : ''
                    }  ${errors.password
                      ? ''
                      : ''
                    } `}
                >
                  <div className="input-box">
                    <IoLockClosed className="icon" />
                    <input
                      type={showPassword ? 'text' : 'password'}
                      className=''
                      value={password}
                      onChange={handleChangePassword}
                      onBlur={handleChangePassword}
                      required
                    />
                    <label>Contraseña</label>
                  </div>

                </div>

                {errors.login && (
                  <span className='text-custom-red text-xs'>{errors.login}</span>
                )}
              </div>

              <button
                type='submit'
                className={`${Object.values(errors).every((value) => value === '')
                    ? ' text-custom-white'
                    : ' border border-custom-blue'
                  }flex justify-center my-btn`}
              >
                Ingresar
              </button>
              {isLoading && <Loading />}

              <div className="register-link">
                <p>Cooperativa 8 de Marzo Ltda.®</p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default LoginPage;