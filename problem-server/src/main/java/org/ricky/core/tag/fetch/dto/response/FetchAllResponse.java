package org.ricky.core.tag.fetch.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.ricky.common.domain.marker.Response;
import org.ricky.core.tag.domain.CachedTag;
import org.ricky.core.tag.domain.Tag;

import java.util.List;

/**
 * @author Ricky
 * @version 1.0
 * @date 2025/1/5
 * @className FetchAllResponse
 * @desc
 */
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FetchAllResponse implements Response {

    List<CachedTag> tags;

}
