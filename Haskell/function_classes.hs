map' :: (a -> b) -> [a] -> [b]
map' f [] = []
map' f (x:xs) = f x : map' f xs

-- map'' f = foldr (\x -> \r -> (:) (f x) r) []
map'' f = foldr (\x -> ((:) . f) x) []

foldr' :: (a -> b -> b) -> b -> [a] -> b
foldr' f z [] = z
foldr' f z (x:xs) = f x (foldr' f z xs)

-- z :: b
-- f :: a -> [a] -> b -> b
recr :: (a -> [a] -> b -> b) -> b -> [a] -> b
recr f z [] = z
recr f z (x:xs) = f x xs (recr f z xs)

length' :: [a] -> Int
length' [] = 0
length' (x:xs) = 1 + length' xs


-- length'' = recr (\x y r -> 1 + r) 0
-- length'' = recr (\x y -> (+ 1)) 0
-- length'' = recr (\x -> const (+ 1)) 0
-- length'' = recr (\x -> const (const (+ 1)) x) 0
length'' = recr (const (const (+ 1))) 0


-- mejorSegun :: (a -> a -> Bool) -> [a] -> a
-- mejorSegun f (x:[]) = x
-- mejorSegun f (x:y:xs) = if (f x y) then mejorSegun f (x:xs) else mejorSegun f (y:xs)

mejorSegun :: (a -> a -> Bool) -> [a] -> a
mejorSegun f = foldl1 (\x rec -> if f x rec then x else rec)

-- sumasParciales :: Num a => [a] -> [a]
-- sumasParciales xs = sumasParciales' [] xs 

-- sumasParciales' :: Num a => [a] -> [a] -> [a]
-- sumasParciales' ac [] = ac
-- sumasParciales' [] (x:xs) = sumasParciales' [x] xs
-- sumasParciales' ac (x:xs) = sumasParciales' (ac ++ [last ac + x]) xs

sumasParciales :: Num a => [a] -> [a]
sumasParciales xs = foldl (\rec x -> rec ++ [(+) x (if null rec then 0 else last rec)]) [] xs

-- sumaAlt [1] ~> 1
-- sumaAlt [1, 2] ~> 1 - 2
-- sumaAlt [1, 2, 3] ~> 1 - 2 + 3
-- sumaAlt [1, 2, 3, 4] ~> 1 - 2 + 3 - 4
--
-- foldr (-) z [a, b, c, d, e]
-- a - (b - (c - (d - (e - z)))))
-- a - b + (c - (d - (e - z)))
-- a - b + c - (d - (...))
--
sumaAlt :: Num a => [a] -> a
sumaAlt = foldr (-) 0

-- sumaAlt' [1,2,3,4,5]
-- 5 - 4 + 3 - 2 + 1
-- 1 - 2 + 3 - 4 + 5
--
--
--  4 - 3 + 2 - 1
--  - 1 + 2 - 3 + 4
-- 
sumaAlt' :: Num a => [a] -> a
-- sumaAlt' = foldl1 (\a b -> b - a)
-- (\a b -> (-) b a)
-- (\a b -> flip (-) a b)
-- (flip (-))
sumaAlt' = foldl1 (flip (-))


agregarPerms :: a -> [a] -> [[a]]
agregarPerms x xs = map (\n -> take n xs ++ [x] ++ drop n xs) [0 .. length xs]

permutaciones :: [a] -> [[a]]
permutaciones = foldr (\x -> concatMap (agregarPerms x)) [[]]

partes :: [a] -> [[a]]
partes = foldr (\x rec -> map (x:) rec ++ rec) [[]]

prefix :: [a] -> [[a]]
prefix = foldl (\ac x -> (head ac ++ [x]) : ac) [[]]

-- TODO: take' :: Int -> [b] -> [b]
-- take' = foldr (\x rec n -> if n>0 then x:(rec (n-1)) else []) (const [])
--





