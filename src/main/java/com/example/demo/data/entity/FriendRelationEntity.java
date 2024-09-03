package com.example.demo.data.entity;

import com.example.demo.data.convert.UserConvert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "friend_relation")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FriendRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(columnDefinition = "TEXT", name = "friend_list")
    @Convert(converter = UserConvert.class)
    private List<Long> friendList;

}
