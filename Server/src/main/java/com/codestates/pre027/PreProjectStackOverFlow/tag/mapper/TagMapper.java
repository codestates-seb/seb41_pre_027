package com.codestates.pre027.PreProjectStackOverFlow.tag.mapper;

import com.codestates.pre027.PreProjectStackOverFlow.tag.dto.TagDto;
import com.codestates.pre027.PreProjectStackOverFlow.tag.dto.TagDto.Response;
import com.codestates.pre027.PreProjectStackOverFlow.tag.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    List<Response> tags_to_TagResponseDtos(List<Tag> tags);
    default List<Tag> tagPostDto_to_Tags(TagDto.Post tagPostDto){
        List<Tag> tags = new ArrayList<>();
        String[] tagArray = tagPostDto.getTagString().split(",");
        for(int i=0; i<tagArray.length;i++){
            if(!tagArray[i].isBlank()) {
                Tag tag = new Tag();
                tag.setTagName(tagArray[i]);
                tags.add(tag);
            }
        }
        return tags;
    }
}
