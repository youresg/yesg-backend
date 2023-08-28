package youresg.yesg.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import youresg.yesg.component.DatabaseCleaner;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.domain.member.SocialProvider;
import youresg.yesg.dto.member.MemberDto;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired DatabaseCleaner databaseCleaner;
    @Autowired MemberRepository memberRepository;
    @Autowired IMemberService memberService;

    private Member member1;
    private Member member2;
    private Member member3;


    @BeforeEach
    void setUp() {
        // given
        member1 = Member.builder()
                .username("member1")
                .email("test1@gmail.com")
                .socialProvider(SocialProvider.GOOGLE)
                .bio("Hello world !")
                .company("Sejong Univ.")
                .location("Seoul, Republic of Korea").build();

        member2 = Member.builder()
                .username("member2")
                .email("test2@gmail.com")
                .socialProvider(SocialProvider.GOOGLE)
                .bio("Hello world !")
                .company("Sejong Univ.")
                .location("Seoul, Republic of Korea").build();

        member3 = Member.builder()
                .username("member3")
                .email("test3@gmail.com")
                .socialProvider(SocialProvider.GOOGLE)
                .bio("Hello world !")
                .company("Sejong Univ.")
                .location("Seoul, Republic of Korea").build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.truncateAllEntity();
    }

    @Test
    void logout() {
    }

    @Test
    void isUsernameValid() {
        // when
        boolean isUsernameValid1 = memberService.isUsernameValid("member1");
        boolean isUsernameValid2 = memberService.isUsernameValid("hello");

        // then
        assertThat(isUsernameValid1).isEqualTo(false);
        assertThat(isUsernameValid2).isEqualTo(true);
    }

    @Test
    void findMemberById() {
        // when
        MemberDto memberDto = memberService.findMemberById(member1.getId()).get();

        // then
        assertThat(memberDto.getUsername()).isEqualTo(member1.getUsername());
        assertThat(memberDto.getEmail()).isEqualTo(member1.getEmail());
        assertThat(memberDto.getBio()).isEqualTo(member1.getBio());
    }

    @Test
    void findByUsername() {
        // when
        MemberDto memberDto = memberService.findByUsername(member1.getUsername()).get();

        // then
        assertThat(memberDto.getId()).isEqualTo(member1.getId());
        assertThat(memberDto.getEmail()).isEqualTo(member1.getEmail());
        assertThat(memberDto.getBio()).isEqualTo(member1.getBio());
    }

    @Test
    void updateMember() {
        MemberDto updateMemberDto = MemberDto.builder()
                .bio("Hello world ! updated")
                .company("Sejong Univ. updated").build();

        // when
        MemberDto updatedMemberDto = memberService.updateMember(member1.getId(), updateMemberDto);

        // then
        assertThat(updatedMemberDto.getLocation()).isEqualTo(member1.getLocation());
        assertThat(updatedMemberDto.getBio()).isEqualTo(updateMemberDto.getBio());
        assertThat(updatedMemberDto.getBio()).isNotEqualTo(member1.getBio());
        assertThat(updatedMemberDto.getCompany()).isEqualTo(updateMemberDto.getCompany());
        assertThat(updatedMemberDto.getCompany()).isNotEqualTo(member1.getCompany());
    }
}