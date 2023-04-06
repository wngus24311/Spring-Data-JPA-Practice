package org.zerock.ex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.zerock.ex2.entity.Memo;

@Controller
public interface MemoRepository extends JpaRepository<Memo, Long> {
}