package mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {

	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void join(UserVo userVo) {
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userRepository.insert(userVo);
	}

	public UserVo getUser(String email, String password) {
		UserVo vo = userRepository.findByEmailAndPassword(email,password);
		return vo;
		
	}
	
	public UserVo getUser(Long id) {
		UserVo vo = userRepository.findById(id);
		//System.out.println(vo.toString());
		return vo;
		
	}
	public void update(UserVo userVo) {
		userRepository.updateUser(userVo);
	}

	public UserVo getUser(String email) {
		UserVo userVo = userRepository.findByEmail(email, UserVo.class);
		userVo.setPassword("");
		return userVo;
	}
}
