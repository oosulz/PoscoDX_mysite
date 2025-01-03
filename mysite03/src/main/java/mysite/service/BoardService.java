package mysite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {

	private static final int PAGE_SIZE = 5;
	private static final int PAGE_BLOCK = 5;

	@Autowired
	private BoardRepository boardRepository;

	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);
	}

	public BoardVo getContents(Long id) {
		BoardVo vo = boardRepository.findById(id);
		boardRepository.plusHit(vo.getId());
		return vo;
	}

	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}

	public void deleteContents(Long id) {
		boardRepository.deleteById(id);
	}

	public Map<String, Object> getContentList(int currentPage) {

		int totalCount = boardRepository.getTotalCount();

		int totalPage = (int) Math.ceil((double) totalCount / PAGE_SIZE);

		int startPage = ((currentPage - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;
		int endPage = startPage + PAGE_BLOCK - 1;
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		boolean hasPrev = currentPage > 1;
		boolean hasNext = currentPage < totalPage;

		List<Integer> disabledPages = new ArrayList<>();
		for (int i = endPage + 1; i <= startPage + PAGE_BLOCK - 1; i++) {
			disabledPages.add(i);
		}

		List<BoardVo> boardList = boardRepository.getList(currentPage, PAGE_SIZE);

		Map<String, Object> result = new HashMap<>();

		result.put("boardlist", boardList);
		result.put("currentPage", currentPage);
		result.put("totalPage", totalPage);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("hasPrev", hasPrev);
		result.put("hasNext", hasNext);
		result.put("pageBlock", PAGE_BLOCK);
		result.put("pageSize", PAGE_SIZE);
		result.put("disabledPages", disabledPages);
		result.put("totalCount", totalCount);

		return result;
	}

}
