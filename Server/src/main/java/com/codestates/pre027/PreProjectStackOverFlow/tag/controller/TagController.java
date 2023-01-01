package com.codestates.pre027.PreProjectStackOverFlow.tag.controller;

import com.codestates.pre027.PreProjectStackOverFlow.auth.jwt.JwtTokenizer;
import com.codestates.pre027.PreProjectStackOverFlow.tag.dto.TagDto;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.Tag;
import com.codestates.pre027.PreProjectStackOverFlow.tag.mapper.TagMapper;
import com.codestates.pre027.PreProjectStackOverFlow.tag.service.TagService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final JwtTokenizer jwtTokenizer;

    public TagController(TagService tagService, TagMapper tagMapper, JwtTokenizer jwtTokenizer){
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/questions/{quest-id}/tags")
    public ResponseEntity postTagsToQuestion(@RequestHeader(name = "Authorization") String token,
        @PathVariable("quest-id") @Positive long questId,
        @Valid @RequestBody TagDto.Post tagPostDto){
        System.out.println("tag 컨트롤러 도착");
        List<Tag> tags = tagService.createTags(tagMapper.tagPostDto_to_Tags(tagPostDto),questId,jwtTokenizer.getMemberId(token));

        List<TagDto.Response> response = tagMapper.tags_to_TagResponseDtos(tags);

        return new ResponseEntity<>(
            response,
            HttpStatus.CREATED);
    }

    @GetMapping("/tags")
    public ResponseEntity getTags(@PageableDefault(size = 10, sort = "tagId", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Tag> tagPage = tagService.findTags(pageable);
        List<Tag> tags = tagPage.getContent();

        List<TagDto.Response> responses = tagMapper.tags_to_TagResponseDtos(tags);

        return new ResponseEntity<>(responses,
            HttpStatus.OK);
    }
}
