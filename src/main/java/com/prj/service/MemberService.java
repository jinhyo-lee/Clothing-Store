package com.prj.service;

import com.prj.dto.MemberDTO;
import com.prj.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberMapper mapper;

    public int login(MemberDTO member) {
        return mapper.login(member);
    }

    public int idCheck(String id) {
        return mapper.idCheck(id);
    }

    public void join(MemberDTO member) {
        mapper.join(member);
    }

    public MemberDTO getMember(String id) {
        return mapper.getMember(id);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MemberDTO member = mapper.getMember(id);

        if (member == null || member.getEnable() == 0)
            throw new UsernameNotFoundException(id);

        return User.builder().username(id).password(member.getPassword()).roles(member.getRole()).build();
    }

}
