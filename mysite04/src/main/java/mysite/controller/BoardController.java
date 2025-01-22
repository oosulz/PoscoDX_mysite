package mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.repository.BoardRepository;
import mysite.security.Auth;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BoardService boardService;

	@GetMapping
	public String BoardList(@RequestParam(name = "pageNum", defaultValue = "1") int boardPage, Model model) {
		Map<String, Object> result = boardService.getContentList(boardPage);
		model.addAllAttributes(result);

		return "board/list";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String BoardDetail(@RequestParam("id") Long id, Model model) {
		BoardVo result = boardService.getContents(id);
		model.addAttribute("boardContent", result);
		return "board/view";
	}
	@Auth
	@RequestMapping(value = "/delete")
	public String BoardDelete(@RequestParam("id") Long id) {
		boardService.deleteContents(id);
		return "redirect:/board?pageNum=1";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String BoardWriteList() {
		return "board/write";
	}
	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String BoardReplyWriteList(@RequestParam("id") Long id, Model model) {
		BoardVo boardVo = boardRepository.findById(id);
		System.out.println(boardVo.toString());
		model.addAttribute("currentboard", boardVo);
		return "board/write";
	}
	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String BoardModifyWriteList(@RequestParam("id") Long id, Model model) {
		BoardVo boardVo = boardRepository.findById(id);
		System.out.println(boardVo.toString());
		model.addAttribute("boardContent", boardVo);
		return "board/modify";
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String BoardModifyList(	@RequestParam("id") Long id, 
									@RequestParam(value = "title") String title,
									@RequestParam(value = "content") String content, Model model) {
		BoardVo vo = new BoardVo();
		vo.setId(id);
		vo.setTitle(title);
		vo.setContents(content);
		boardService.updateContents(vo);
		
		return "redirect:/board/detail?id=" + id;
	}
	 
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String BoardWrite(@RequestParam(value = "GNo", required = false) String GNo,
			@RequestParam(value = "ONo", required = false) String ONo,
			@RequestParam(value = "depth", required = false) String depth, @RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content, @RequestParam(value = "userid") Long userid) {

		BoardVo vo = new BoardVo();

		if (GNo != null && !GNo.isEmpty() && ONo != null && !ONo.isEmpty() && depth != null && !depth.isEmpty()) {
			vo.setGNo(Integer.parseInt(GNo));
			vo.setONo(Integer.parseInt(ONo) + 1);
			vo.setDepth(Integer.parseInt(depth) + 1);
		}

		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserId(userid);

		System.out.println(vo.toString());

		boardService.addContents(vo);

		return "redirect:/board?pageNum=1";
	}

}
